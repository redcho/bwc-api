SRC_DIR="./com/bigcode/proto"
DST_DIR="./"
protoc -I=$SRC_DIR --java_out=$DST_DIR $SRC_DIR/*.proto