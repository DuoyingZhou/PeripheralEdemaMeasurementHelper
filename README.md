# PeripheralEdemaMeasurementHelper
This is an Android application which outputs edema dent duration time and edema stage given patient's skin dent video as the input, providing patients with a cost-effective and user-friendly way to do the edema measurement. In this application, dent duration (the time that dent remains on human’s skin) is the parameter used to measure edema. 
# Main functions and folders:
EdemaMeasurementHelper-master:   
        implement video shooting, video uploading, and data visualization in Android app by using Android Studio  
detection.py:  
        detect that if a new file is uploaded into EC2 server and do the following processing  
svmpit.py:  
        train a linear SVM classifier using HOG features of images with pit and without pit  
predict.py:  
        apply the linear SVM classifier on predicting the class of a new frame read from a video  
dynamodb.py:  
        store the results of a pitting test video including edema dent duration and edema stage into AWS DynamoDB  
