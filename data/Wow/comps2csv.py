import os
import urllib
import json
import csv

f = csv.writer(open("comps.csv", "w+"))
f.writerow(["id", "username", "make", "model", "hardrive", "processor", "os", "ram", "price", "description", "status", "time"])

response = urllib.urlopen('http://test-technologic.rhcloud.com/computers/_search')
my_json = json.loads(response.read())

#print my_json['hits']['hits']

for comp in my_json['hits']['hits']:
  make =  comp['_source']['make']
  model =  comp['_source']['model']
  id1 =  comp['_source']['id']
  hardrive =  comp['_source']['hardDrive']
  description =  comp['_source']['description']
  time =  comp['_source']['time']
  username = comp['_source']['username']
  processor = comp['_source']['processor']
  os = comp['_source']['os']
  ram = comp['_source']['ram']
  price = comp['_source']['price']
  status = comp['_source']['status']

  print id1, username, make, model, hardrive, processor, os, ram, price, description, status, time
  print "\n"
  f.writerow([id1, username, make, model, hardrive, processor, os, ram, price, description, status, time])


  