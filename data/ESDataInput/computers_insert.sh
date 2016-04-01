while read line; do
	curl -XPOST 'http://test-technologic.rhcloud.com/computers/computer/' -d "$line"
done < $1