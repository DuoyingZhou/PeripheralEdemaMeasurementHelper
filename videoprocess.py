import cv2
import glob
import numpy as np
def videocrop(filename):
	vidcap = cv2.VideoCapture(filename)
	#success,image = vidcap.read()
	length = int(vidcap.get(cv2.CAP_PROP_FRAME_COUNT))
	fps = int(round(vidcap.get(cv2.CAP_PROP_FPS)))
	# print(length)
	# print(fps)
	# setting the interval here (default: 1 sec)
	intervalseconds = 10
	count = 0
	success = True
	interval = intervalseconds * fps
	croppedimage = 0
	while success:
		success,image = vidcap.read()
		print('Read a new frame: ', success)
		if not success:
			break
		if count % interval == 0:
			r = cv2.selectROI(image)
			imCrop = image[int(r[1]):int(r[1]+r[3]), int(r[0]):int(r[0]+r[2])]
			cv2.imwrite(filename + "_frame%d.jpg" % count, imCrop)
			croppedimage = croppedimage + 1
		if croppedimage == 3:
			break
		count += 1

def stack3image(filename):
	imagelist = glob.glob(filename + '*.jpg')
	print(imagelist)
	arrays = []
	for image in imagelist:
		print(image)
		im = cv2.imread(image)
		im = cv2.cvtColor(im, cv2.COLOR_BGR2GRAY)
		im = cv2.resize(im, (227,227))
		print(im)
		arrays.append(im)
	print(arrays)
	newimage = np.stack(arrays, axis = 2)
	cv2.imwrite(path + filename + "_stackedimage.jpg", newimage)

if __name__ == '__main__':
	filename = "4_0_Lounge_HONOR7iAndroid_00033.mp4"
	path = "E:/study/Fall2017/research/4/stackedimage/"
	videocrop(filename)
	stack3image(filename)