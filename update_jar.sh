#!/bin/bash
mkdir deployment_files
cp -r $PWD/$APPLICAION_DIR/target/azure-functions/odd-or-even-function-sample/* deployment_files
