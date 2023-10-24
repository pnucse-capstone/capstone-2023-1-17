#!/bin/bash

id=$1

#process
ns-process-data video --data /workspace/video/$id.mp4 --output-dir /workspace/result/data/$id

#train
ns-train nerfacto --data /workspace/result/data/$id --output-dir /workspace --experiment-name result --method-name train --timestamp $id --pipeline.model.predict-normals True --viewer.quit-on-train-completion True --logging.steps-per-log 30001

#export
ns-export poisson --load-config /workspace/result/train/$id/config.yml --output-dir /workspace/result/mesh/$id --target-num-faces 50000 --num-pixels-per-side 2048 --normal-method model_output --num-points 1000000 --remove-outliers True --use-bounding-box True --bounding-box-min -1 -1 -1 --bounding-box-max 1 1 1

#HTTP message(inform server nerf is done)
curl -X GET "http://localhost:3389/finishNerf?id=$id"
