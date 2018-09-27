import cognitive_face as CF
import requests
from io import BytesIO
from PIL import Image, ImageDraw
import requests,json
import urllib
import numpy as np
import matplotlib.pyplot as plt
import urllib3
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)


KEY = '8f046261e7b14328b440b5729fce01bb'
CF.Key.set(KEY)
BASE_URL = 'https://southeastasia.api.cognitive.microsoft.com/face/v1.0/'  # Replace with your regional Base URL
CF.BaseUrl.set(BASE_URL)


# You can use this example JPG or replace the URL below with your own URL to a JPEG image.
image1 = 'https://raw.githubusercontent.com/Microsoft/Cognitive-Face-Windows/master/Data/detection1.jpg'
image2 = 'https://firebasestorage.googleapis.com/v0/b/songsang-finalproject-image.appspot.com/o/849725174.jpg.0.jpg?alt=media&token=eac6b061-0425-43b0-9171-76de86c16240'
image3 = 'https://firebasestorage.googleapis.com/v0/b/songsang-finalproject-image.appspot.com/o/face3.jpg?alt=media&token=b79de5fc-fe9e-488c-83df-205e09dd7298'
img_url1 = image1
img_url2 = image2
img_url3 = image3
faces1 = CF.face.detect(img_url1)
faces2 = CF.face.detect(img_url2)
faces3 = CF.face.detect(img_url3)
print(faces1)
print(faces2)
print(faces3)

urlList = [img_url1,img_url2,img_url3]

result = []
for url in urlList:
        r = CF.face.detect(url)
        result += r,

all_faceid = [f['faceId'] for image in result for f in image]

test_url = 'https://firebasestorage.googleapis.com/v0/b/songsang-finalproject-image.appspot.com/o/ap_18033636089764_sq-84ebd546b0e2e862c2de61ea385262435a6a19ff-s900-c85.jpg?alt=media&token=6004828f-3f12-486c-a212-3a521c7aa3a3'
test_result = CF.face.detect(test_url)
test_faceId = test_result[0]['faceId']

for f in all_faceid:
  r = CF.face.verify(f, test_faceId)
  print (r)

def getRectangle(faceDictionary):
    rect = faceDictionary['faceRectangle']
    left = rect['left']
    top = rect['top']
    bottom = left+ rect['height']
    right = top+ rect['width']
    return ((left, top), (bottom, right))


response = requests.get(img_url3)
img = Image.open(BytesIO(response.content))

draw = ImageDraw.Draw(img)
for face in faces3: draw.rectangle(getRectangle(face), outline='red')

img.show()