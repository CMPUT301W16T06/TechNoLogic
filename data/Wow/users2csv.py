import os
import urllib
import json
import csv

f = csv.writer(open("users.csv", "w+"))
d = open("results.txt", "a+")
f.writerow(["id", "username", "name", "phone", "email", "address", "time"])

response = urllib.urlopen('http://test-technologic.rhcloud.com/users/_search')
my_json = json.loads(response.read())

#print my_json['hits']['hits']
count = 0

for user in my_json['hits']['hits']:
  address =  user['_source']['address']
  email =  user['_source']['email']
  id1 =  user['_source']['id']
  name =  user['_source']['name']
  phone =  user['_source']['phone']
  time =  user['_source']['time']
  username =  user['_source']['username']
  print id1, username, name, phone, email, address, time
  print "\n"
  count = count + 1
  f.writerow([id1, username, name, phone, email, address, time])

count = str(count)
d.write("The number of current users:" + count)
d.write("\n")
d.close()



  