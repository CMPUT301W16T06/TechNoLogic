import os
import urllib
import json
import csv

f = csv.writer(open("bids.csv", "w+"))
f.writerow(["bidid", "computerid", "owner", "username", "price", "time"])

response = urllib.urlopen('http://test-technologic.rhcloud.com/bids/_search')
my_json = json.loads(response.read())

#print my_json['hits']['hits']

for comp in my_json['hits']['hits']:
  bidid =  comp['_source']['bidID']
  computerid =  comp['_source']['computerID']
  owner =  comp['_source']['owner']
  time =  comp['_source']['time']
  username = comp['_source']['username']
  price = comp['_source']['price']

  print bidid, computerid, owner, username, price, time
  print "\n"
  f.writerow([bidid, computerid, owner, username, price, time])


  