# FACTURE Extraction App


## LM Studio

### Modèle

* Model: LiquiAI/LFM2.5-VL-450M-GGUF
* key: lfm2.5-vl-450m

### Server

Doc : https://lmstudio.ai/docs/developer/rest/quickstart

```bash
lms server start --bind 0.0.0.0
```


Test :

```bash
curl http://localhost:1234/api/v1/chat \
  -H "Content-Type: application/json" \
  -d '{
    "model": "lfm2.5-vl-450m",
    "input": "Write a short haiku about sunrise."
  }'
```

Sous wls

```bash

curl http://$(hostname).local:1234/api/v1/chat \
  -H "Content-Type: application/json" \
  -d '{
    "model": "lfm2.5-vl-450m",
    "input": "Write a short haiku about sunrise."
  }'
```
