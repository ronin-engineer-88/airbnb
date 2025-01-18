#!/bin/bash

# Config
ES_HOST="https://localhost:9200"
INDEX_NAME="airbnb_listings"
API_KEY=  # Generated API key from Kibana
CA_CERT_PATH="./ca.crt"

TOTAL_DOCS=10000000
BULK_SIZE=5000  # Number of documents per batch

PROPERTY_TYPES=("Apartment" "House" "Hotel" "GuestHouse")

generate_bulk_data() {
  for ((i=1; i<=$1; i++)); do
    # Generate a random range for avg_price
    avg_price_min=$(awk -v min=50 -v max=400 'BEGIN{srand(); print min+rand()*(max-min)}')
    avg_price_max=$(awk -v min=400 -v max=500 'BEGIN{srand(); print min+rand()*(max-min)}')

    # Ensure avg_price_min is less than avg_price_max
    if (( $(echo "$avg_price_min > $avg_price_max" | bc -l) )); then
      temp=$avg_price_min
      avg_price_min=$avg_price_max
      avg_price_max=$temp
    fi

    # Randomly select a property type
    property_type=${PROPERTY_TYPES[$((RANDOM % ${#PROPERTY_TYPES[@]}))]}

    random_timestamp=$(( $RANDOM % (1643723900 - 1577836800 + 1) + 1577836800 ))
    random_timestamp=$(( random_timestamp * 1000 ))

    cat <<EOF
{ "index": { "_index": "$INDEX_NAME" } }
{ "property_id": "$RANDOM", "title": "Sample Title $i", "property_type": "$property_type", "description": "This is a generated description for property $i", "avg_price": { "gte": $avg_price_min, "lte": $avg_price_max }, "location": { "lat": $(awk -v min=-90 -v max=90 'BEGIN{srand(); print min+rand()*(max-min)}'), "lon": $(awk -v min=-180 -v max=180 'BEGIN{srand(); print min+rand()*(max-min)}') }, "amenities": ["WiFi", "Air Conditioning", "Pool"], "rating": $(awk -v min=1 -v max=5 'BEGIN{srand(); print min+rand()*(max-min)}'), "host": { "host_id": "$RANDOM", "host_name": "Host $i", "host_language": "English" }, "images": ["https://example.com/image$i.jpg"], "created_at": "$random_timestamp" }
EOF
  done
}

#echo "$(generate_bulk_data $BULK_SIZE)"

# Generate and send bulk data
for ((start=0; start<$TOTAL_DOCS; start+=$BULK_SIZE)); do
  echo "-----------------"
  echo "Indexing documents $((start+1)) to $((start+BULK_SIZE))..."
  generate_bulk_data $BULK_SIZE | curl -s --cacert $CA_CERT_PATH -X POST "$ES_HOST/_bulk" -H "Content-Type: application/json" -H "Authorization: ApiKey $API_KEY" --data-binary @-
done

echo "Completed indexing $TOTAL_DOCS documents."