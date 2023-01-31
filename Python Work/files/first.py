import cv2
import numpy as np
import face_recognition

imgElon = face_recognition.load_image_file("images/Elon Musk.jpg")
imgElon = cv2.cvtColor(imgElon,cv2.COLOR_RGB2BGR)

imgTest = face_recognition.load_image_file("images/Elon Test.jpg")
imgTest = cv2.cvtColor(imgTest,cv2.COLOR_RGB2BGR)

faceLoc = face_recognition.face_locations(imgElon)[0]
encodeElon = face_recognition.face_encodings(imgElon)[0]

cv2.rectangle(imgElon,(faceLoc[3],faceLoc[0]),(faceLoc[1],faceLoc[2]),(255,0,255),2)

faceLocTest = face_recognition.face_locations(imgTest)[0]
encodeTest = face_recognition.face_encodings(imgTest)[0]

cv2.rectangle(imgTest,(faceLocTest[3],faceLocTest[0]),(faceLocTest[1],faceLocTest[2]),(0,255,0),2)

results = face_recognition.compare_faces([encodeElon],encodeTest)
faceDistance = face_recognition.face_distance([encodeElon],encodeTest)
print(results,faceDistance)

cv2.imshow("Elon Musk",imgElon)
cv2.imshow("Elon Test",imgTest)

cv2.waitKey(0)
