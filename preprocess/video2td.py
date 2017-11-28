import os
import cv2
import numpy as np
import matplotlib.pyplot as plt

def video2frame():

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
    while ret:
        # read from specific time point
        cap.set(1, frame_no)
        frame_no += int(fps)
        image_id += 1
        if frame_no + 2*int(fps) > video_len or image_id > 999:
            break

        stage = np.zeros((512,512,3))
        for step in range(3):
            frame_no = frame_no + step * int(fps) * 1
            cap.set(1, frame_no)
            # read a frame
            ret, frame = cap.read()
            # color to gray
            gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
            # resize image
            gray = cv2.resize(gray, (1024, 1024))
            # crop image
            gray = gray[256:768, 256:768]#[200:712, 190:702]
            # denoise
            gray = cv2.fastNlMeansDenoising(gray, None, 65, 2, 21)
            # stack images of different time together
            stage[:,:,step] = gray

        # show video
        cv2.imshow("td video", stage)

        # save images
        save_name = stage_name + str(image_id).zfill(4) + ".png"
        cv2.imwrite(os.path.join(save_path, save_name), stage)
        print(save_name)
        
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break


    # When everything done, release the capture
    cap.release()
    cv2.destroyAllWindows() 
        
    
    

if __name__ == "__main__":
    video2frame()
