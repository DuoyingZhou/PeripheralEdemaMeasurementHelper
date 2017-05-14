import cv2
import cv2.ml
import numpy as np
import glob
from glob import glob
import pickle
from sklearn import svm
from sklearn.externals import joblib
hog=cv2.HOGDescriptor((64,128),(16,16),(8,8),(8,8),9)
img_mask='/home/doria/Downloads/svm_pit/positive/*'
img_names=glob(img_mask)
pos_num=len(img_names)
print pos_num
i=0
feature_pos=[]
for fn in img_names:
    image=cv2.imread(fn)
    dim=(64,128)
    image = cv2.resize(image,dim,interpolation=cv2.INTER_AREA)
    aa=np.transpose(hog.compute(image))
    feature_pos.append(aa.tolist()[0])
    i=i+1
    print i
print aa
img_mask='/home/doria/Downloads/svm_pit/negative/*'
img_names=glob(img_mask)
neg_num=len(img_names)
print neg_num
feature_neg=[]
for fn in img_names:
    image=cv2.imread(fn)
    dim = (64,128)
    image = cv2.resize(image, dim, interpolation = cv2.INTER_AREA)
#    print image.shape
    bb=np.transpose(hog.compute(image)).tolist()
    
    feature_neg.append(bb[0])
    i=i+1
    print i

feature=feature_pos+feature_neg
clas=[1]*pos_num+[-1]*neg_num

clf2=svm.SVC(kernel='linear',C=1).fit(feature,clas)
joblib.dump(clf2,"kernellinear.pkl")


