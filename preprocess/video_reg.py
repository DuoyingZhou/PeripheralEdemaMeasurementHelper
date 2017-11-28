import os
import cv2
import numpy as np
import matplotlib.pyplot as plt

def get_gradient(im) :
    # Calculate the x and y gradients using Sobel operator
    grad_x = cv2.Sobel(im,cv2.CV_32F,1,0,ksize=3)
    grad_y = cv2.Sobel(im,cv2.CV_32F,0,1,ksize=3)
 
    # Combine the two gradients
    grad = cv2.addWeighted(np.absolute(grad_x), 0.5, np.absolute(grad_y), 0.5, 0)
    return grad

def main():

    video_path = "../video/"
    video_name = "1_0_Lounge_iphone7_10002.mp4"
    cap = cv2.VideoCapture(video_path + video_name)
    video_len = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
    fps = cap.get(cv2.CAP_PROP_FPS)
    print("frame rate per second: {}".format(fps))
    # define save directory
    save_path = "../predata/td_frames"
    stage_name = "stage1_"

    frame_no = 4  * int(fps)
    image_id = 0
    ret = True
    last_frame = None

    # registration settings
    # Define motion model
    warp_mode = cv2.MOTION_HOMOGRAPHY
 
    # Set the warp matrix to identity.
    if warp_mode == cv2.MOTION_HOMOGRAPHY :
            warp_matrix = np.eye(3, 3, dtype=np.float32)
    else :
            warp_matrix = np.eye(2, 3, dtype=np.float32)
 
    # Set the stopping criteria for the algorithm.
    criteria = (cv2.TERM_CRITERIA_EPS | cv2.TERM_CRITERIA_COUNT, 5000,  1e-10)

    frame_no = int(fps) * 4
    cap.set(1, frame_no)
    while ret:
        # read frame
        ret, frame = cap.read()
        # color to gray
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        # resize image
        gray = cv2.resize(gray, (1024, 1024))
        # crop image
        gray = gray[256:768, 256:768]#[200:712, 190:702]
        # denoise
        gray = cv2.fastNlMeansDenoising(gray, None, 65, 2, 21)

        if last_frame == None:
            last_frame = gray
            continue
        else:
            # get transformation matrix
            grad1 = get_gradient(last_frame)
            grad2 = get_gradient(gray)
            (cc, warp_matrix) = cv2.findTransformECC (cv2.resize(last_frame,(64,64)), cv2.resize(gray,(64,64)),warp_matrix, warp_mode, criteria)
            # Use Perspective warp when the transformation is a Homography
            gray = cv2.warpPerspective (gray, warp_matrix, (512,512), flags=cv2.INTER_LINEAR + cv2.WARP_INVERSE_MAP)
            # update last frame
            last_frame = gray

        print("playing video...")

        # show video
        cv2.imshow("aligned video", gray)
        cv2.imshow("last frame", last_frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break


    # When everything done, release the capture
    cap.release()
    cv2.destroyAllWindows() 
        
    
    

if __name__ == "__main__":
    main()
