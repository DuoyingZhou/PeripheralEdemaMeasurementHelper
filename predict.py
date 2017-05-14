import cv2
from sklearn.externals import joblib
import numpy as np
import glob
from glob import glob
hog = cv2.HOGDescriptor((64,128),(16,16),(8,8),(8,8),9)
#img_mask='/home/doria/Downloads/test/pos/*'
#img_names=glob(img_mask)
def predict(filename):
	cap = cv2.VideoCapture(filename)
	fps = cap.get(cv2.CAP_PROP_FPS)
	ret, frame = cap.read()
	fea=[]
	while ret:
		#image=cv2.imread(fn)
		dim=(64,128)
		image=cv2.resize(frame, dim, interpolation=cv2.INTER_AREA)
		fea.append(hog.compute(image).reshape(1,3780)[0])
		ret, frame = cap.read()
	fea=np.array(fea)
	ccc=joblib.load("kernellinear.pkl")
	list = ccc.predict(fea)
	numofone = 0
	for item in list:
	    if item==1:
			numofone = numofone +1
	dentduration = float(numofone)/fps
	if dentduration <= 5:
		stage = 1
	elif dentduration <= 25:
		stage = 2
	elif dentduration <=60:
		stage = 3
	else:
		stage = 4
	return dentduration, stage
