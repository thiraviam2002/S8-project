import cv2
import streamlit as st
import os
vidcap = cv2.VideoCapture("vijay.mp4")

try:
    
    if not os.path.exists('data'):
        os.makedirs('data')

except OSError:
    print("Error : Creating directory of data")

currentframe=0

while(True):
    ret,frame=vidcap.read()

    if ret:

        name='./data/frame' + str(currentframe) +".jpg"
        print("creating .."+ name)
        cv2.imwrite(name,frame)
        currentframe+=1
    else:
        break

vidcap.release()
cv2.destroyAllWindows()


