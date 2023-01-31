from tkinter import*
from PIL import Image,ImageTk
import cv2
import numpy as np
import face_recognition
import os

path = 'E:\Python Work\pictures'

images = []
classNames = []
myList = os.listdir(path)

for cl in myList:
    curImg = cv2.imread(f'{path}/{cl}')
    images.append(curImg)
    classNames.append(os.path.splitext(cl)[0])

def findEncodings(images):
    encodeList = []
    for img in images:
        img = cv2.cvtColor(img,cv2.COLOR_BGR2RGB)
        encode = face_recognition.face_encodings(img)[0]
        encodeList.append(encode)
    return encodeList

encodeListKnown = findEncodings(images)
print('Encoding Completed')

win = Tk()
win.geometry("600x320")
win.resizable(False, False)
win.configure(bg ='#1b407a')
w = 400
h = 300

color = "#00FFFF"
frame_1 = Frame(win,width = 600,height =320,bg = color).place(x=150,y=0)

v = Label(frame_1, width=w, height=h)
v.place(x=100, y=10)
cap = cv2.VideoCapture(0)

def select_img():
    success, img = cap.read()
    imgS = cv2.resize(img,(0,0), None, 0.25, 0.25)
    imgS = cv2.cvtColor(imgS, cv2.COLOR_BGR2RGB)

    facesCurFrame = face_recognition.face_locations(imgS)
    encodesCurFrame = face_recognition.face_encodings(imgS,facesCurFrame)

    for encodeFace,faceLoc in zip(encodesCurFrame,facesCurFrame):
        matches = face_recognition.compare_faces(encodeListKnown,encodeFace)
        faceDis = face_recognition.face_distance(encodeListKnown,encodeFace)
        # print(faceDis)
        matchIndex = np.argmin(faceDis)

        if matches[matchIndex]:
            name = classNames[matchIndex].upper()
            # print(name)
            y1, x2, y2, x1 = faceLoc
            y1, x2, y2, x1 = y1*4, x2*4, y2*4, x1*4
            cv2.rectangle(img, (x1,y1), (x2,y2), (0,255,0), 2)
            cv2.rectangle(img, (x1,y2-35), (x2,y2), (0,255,0), cv2.FILLED)
            cv2.putText(img, name, (x1+6,y2-6), cv2.FONT_HERSHEY_COMPLEX, 1, (255,255,255), 2)
        elif not matches[matchIndex]:
            y1, x2, y2, x1 = faceLoc
            y1, x2, y2, x1 = y1*4, x2*4, y2*4, x1*4
            cv2.rectangle(img, (x1,y1), (x2,y2), (0,0,255), 2)
            cv2.rectangle(img, (x1,y2-35), (x2,y2), (0,0,255), cv2.FILLED)
            cv2.putText(img, 'UNKNOWN', (x1+6,y2-6), cv2.FONT_HERSHEY_COMPLEX, 1, (255,255,255), 2)
    global rgb
    _ = cap.read()
    img = cv2.resize(img, (w, h))
    rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    image = Image.fromarray(rgb)
    imgtk = ImageTk.PhotoImage(image)
    v.configure(image=imgtk)
    v.image = imgtk
    v.after(1, select_img)

select_img()

win.mainloop()