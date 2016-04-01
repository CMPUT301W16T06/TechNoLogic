import os
import urllib
import json
import csv

f = csv.writer(open("borrows.csv", "w+"))
f.writerow(["borrowid", "computerid", "owner", "username", "latitude", "longitude","time"])

response = urllib.urlopen('http://test-technologic.rhcloud.com/borrows/_search')
my_json = json.loads(response.read())

#print my_json['hits']['hits']

for comp in my_json['hits']['hits']:
  borrowid =  comp['_source']['borrowID']
  computerid =  comp['_source']['computerID']
  owner =  comp['_source']['owner']
  time =  comp['_source']['time']
  username = comp['_source']['username']
  latitude = comp['_source']['latitude']
  longitude = comp['_source']['longitude']
 

  print borrowid, computerid, owner, username, latitude, longitude, time
  print "\n"
  f.writerow([borrowid, computerid, owner, username, latitude, longitude, time])


  