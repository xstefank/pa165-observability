# pa165-2023-lecture10

To correctly run all services you need the following infrastructure:

docker compose up

Or manually:

podman run --rm --name prometheus --network host -v ./prometheus.yml:/etc/prometheus/prometheus.yml:Z prom/prometheus:v3.3.9 --config.file=/etc/prometheus/prometheus.yml

podman run --rm -p 3000:3000 --network host grafana/grafana:11.6.0

podman run --rm -p 9411:9411 openzipkin/zipkin:3.5.0

