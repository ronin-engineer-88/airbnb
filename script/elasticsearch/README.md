# Elasticsearch

## How to run `generate_data.sh`

### Prerequisite
- Generated API key from Kibana
- Export `ca.crt` from `./ca/cart.crt` in volume `certs`
- Make sure call ES Call with `https`, not `http`
- Change config in `generate_data.sh` if needed

### Run
- Open multiple terminals to run and speed up the ingestion process.
```shell
./generate_data.sh
```

### Note
- This way is not optimal yet. Feel free to improve it.

## Console Script
- Please read more in `script/elasticsearch/es-console-script.txt`

