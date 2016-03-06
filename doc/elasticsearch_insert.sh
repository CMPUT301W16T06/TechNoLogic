
while read line; do
	curl -XPOST 'http://test-technologic.rhcloud.com/pc4hire/user/' -d "$line"
done < $1