import cv2
import face_recognition
import numpy as np

imgElon = face_recognition.load_image_file('pictures\Elon Musk.jpg')
imgElon = cv2.cvtColor(imgElon,cv2.COLOR_RGB2BGR)

imgTest = face_recognition.load_image_file('pictures\Bill.jpg')
imgTest = cv2.cvtColor(imgTest,cv2.COLOR_RGB2BGR)

faceLoc = face_recognition.face_locations(imgElon)[0]
faceEncodings = face_recognition.face_encodings(imgElon)[0]
cv2.rectangle(imgElon,(faceLoc[3],faceLoc[0]),(faceLoc[1],faceLoc[2]),(0,255,0),2)

faceLocTest = face_recognition.face_locations(imgTest)[0]
faceEncodingsTest = face_recognition.face_encodings(imgTest)[0]
cv2.rectangle(imgTest,(faceLocTest[3],faceLocTest[0]),(faceLocTest[1],faceLocTest[2]),(0,255,0),2)

result = face_recognition.compare_faces([faceEncodings],faceEncodingsTest)
print(result)

cv2.imshow("Elon Musk",imgElon)
cv2.imshow("Elon Test",imgTest)
cv2.waitKey(0)