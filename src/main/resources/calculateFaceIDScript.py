import sys
import cognitive_face as CF
from io import BytesIO
from PIL import Image, ImageDraw
import requests,json
import urllib3
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

#python3 calculateScript.py 45148831_2095645980475310_3758039432733655040_o.jpg 66ed2e64-444f-4645-b25f-30aa2bfafb1e 44994516_2093429680696940_2268254602195369984_o.jpg a5d3954d-0a88-4376-a7f3-8a28fe3e2dcb

KEY = 'd8572e76b66046b9ac4113d5c94f6b71'
CF.Key.set(KEY)
BASE_URL = 'https://southeastasia.api.cognitive.microsoft.com/face/v1.0/'  # Replace with your regional Base URL
CF.BaseUrl.set(BASE_URL)

FIREBASE_URL = 'https://firebasestorage.googleapis.com/v0/b/facialauthentication-camt.appspot.com/o/ZqWqvktTcOVmFV7N5dMswhc3tGH3%2F'

print (sys.argv[1])
print (sys.argv[2])

#receive list of imagepath from command line
image1 = FIREBASE_URL+sys.argv[1]+"?alt=media&token="+sys.argv[2]
image2 = FIREBASE_URL+sys.argv[3]+"?alt=media&token="+sys.argv[4]

# image1 = 'https://firebasestorage.googleapis.com/v0/b/facialauthentication-camt.appspot.com/o/ZqWqvktTcOVmFV7N5dMswhc3tGH3%2F45148831_2095645980475310_3758039432733655040_o.jpg?alt=media&token=66ed2e64-444f-4645-b25f-30aa2bfafb1e'
# image2 ='https://firebasestorage.googleapis.com/v0/b/facialauthentication-camt.appspot.com/o/ZqWqvktTcOVmFV7N5dMswhc3tGH3%2F44994516_2093429680696940_2268254602195369984_o.jpg?alt=media&token=a5d3954d-0a88-4376-a7f3-8a28fe3e2dcb'

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
original_url = image1
original_result = CF.face.detect(original_url)
original_faceId = original_result[0]['faceId']

originalList = []

for f in all_faceid:
    r = CF.face.verify(f, original_faceId)
    print (r)

