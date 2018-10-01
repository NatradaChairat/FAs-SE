import sys
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

#py file.py originalfile.png image1.png image2.png

KEY = '8f046261e7b14328b440b5729fce01bb'
CF.Key.set(KEY)
BASE_URL = 'https://southeastasia.api.cognitive.microsoft.com/face/v1.0/'  # Replace with your regional Base URL
CF.BaseUrl.set(BASE_URL)


#receive list of imagepath from command line
image1 = sys.argv[2]
image2 = sys.argv[3]
img_url1 = image1
img_url2 = image2
faces1 = CF.face.detect(img_url1)
faces2 = CF.face.detect(img_url2)
print(faces1)
print(faces2)

urlList = [img_url1,img_url2]

result = []
for url in urlList:
        r = CF.face.detect(url)
        result += r,

all_faceid = [f['faceId'] for image in result for f in image]

#original image
original_url = sys.argv[1]
original_result = CF.face.detect(test_url)
original_faceId = test_result[0]['faceId']

for f in all_faceid:
  r = CF.face.verify(f, original_faceId)
  print (r)

def getRectangle(faceDictionary):
    rect = faceDictionary['faceRectangle']
    left = rect['left']
    top = rect['top']
    bottom = left+ rect['height']
    right = top+ rect['width']
    return ((left, top), (bottom, right))


response = requests.get(img_url2)
img = Image.open(BytesIO(response.content))

draw = ImageDraw.Draw(img)
for face in faces3: draw.rectangle(getRectangle(face), outline='red')

img.show()
