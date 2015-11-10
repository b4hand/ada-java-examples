#!/usr/bin/env bash

sudo apt-add-repository ppa:webupd8team/java

sudo apt-get update

echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections

sudo apt-get install -y build-essential oracle-java8-installer maven

sudo apt-get autoremove --purge -y
