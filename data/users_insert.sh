while read line; do
	curl -XPOST 'http://test-technologic.rhcloud.com/users/user/' -d "$line"
done < $1