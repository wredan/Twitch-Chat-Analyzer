#!/bin/bash
rm modules.zip
pushd code/modules/
zip -r ../../modules.zip ./
popd
