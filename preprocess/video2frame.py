import os
import cv2
import numpy as np
import matplotlib.pyplot as plt

def video2frame(video_path, video_name, save_path):

    #video_path = "../video/"
    #video_name = "4_0_Lounge_Iphone7_20001.MOV"
    cap = cv2.VideoCapture(video_path + video_name)
    video_len = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
    fps = cap.get(cv2.CAP_PROP_FPS)
    print("frame rate per second: {}".format(fps))
    # define save directory
    # save_path = "../predata/"
    video_path = video_name.split(".")[0]
    stage_name = "stage" + video_name[0] + "_"

    frame_no = 6 * int(fps)
    ret = True
    image_id = 0
    frame_count = 0
    while ret:
        # read from specific time point
        cap.set(1, frame_no)
        frame_no += 5*int(fps)
        frame_count += 1
        print("frame count: {}".format(frame_count))
        if frame_no > video_len or frame_count > 10:
            break
        # read a frame
        ret, frame = cap.read()
        # color to gray
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        # resize image
        gray = cv2.resize(gray, (1024, 1024))
        # crop image
        gray = gray[:, 256:768]
        gray = cv2.resize(gray, (512, 512))
        print(gray.shape)
        # show video
        cv2.imshow("raw video", gray)
        # save images
        save_name = stage_name + str(image_id).zfill(4) + ".png"
        if not os.path.exists(os.path.join(save_path, video_path)):
            os.mkdir(os.path.join(save_path, video_path))
        cv2.imwrite(os.path.join(save_path, video_path, save_name), gray)
        print(save_name)
        image_id += 1
        
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break


    # When everything done, release the capture
    cap.release()
    cv2.destroyAllWindows()

def main():
    # convert all videos into temporal-downsampled frame data 
    video_path = "../video/"
    video_names = os.listdir(video_path)
    save_path = "../predata/"
    for video_name in video_names:
        if not os.path.exists(os.path.join(save_path, video_name.split(".")[0])):
            video2frame(video_path, video_name, save_path)
        else:
            print(os.path.join(save_path, video_name.split(".")[0]), " exists...")
    

if __name__ == "__main__":
    #video2frame()
    main()

    
