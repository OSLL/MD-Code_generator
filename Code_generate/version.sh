COMMIT=$(git rev-parse HEAD)
DATE=$(date -u +"%Y-%m-%dT%H:%M:%SZ")
VERSION=$(git tag | tail -1)
[ ${#VERSION} == 0 ] && VERSION="no version"
echo "{
\"commit\":  \"$COMMIT\",
\"date\":    \"$DATE\",
\"version\": \"$VERSION\"
}" > version_info.txt