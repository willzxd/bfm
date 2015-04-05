#!/usr/bin/env bash

###################################################################
# The BigFrame configuration parameters
#
# Used to set the user-defined parameters in BigFrame.
#
# Author: Andy He
# Date:   June 16, 2013
###################################################################

###################################################################
# GLOBAL PARAMETERS USED BY DATA GENERATOR (REQUIRED)
###################################################################

# The Hadoop Home Directory
HADOOP_HOME=$HADOOP_HOME
BIGFRAME_OPTS="${BIGFRAME_OPTS} -Dbigframe.hadoop.home=${HADOOP_HOME}"

# Hadoop Conf Directory
HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
BIGFRAME_OPTS="${BIGFRAME_OPTS} -Dbigframe.hadoop.conf.dir=${HADOOP_CONF_DIR}"

# The Hadoop slave file
HADOOP_SLAVES=$HADOOP_HOME/etc/hadoop/slaves
BIGFRAME_OPTS="${BIGFRAME_OPTS} -Dbigframe.hadoop.slaves=${HADOOP_SLAVES}"

# Local Directory to store the temporary TPCDS generated files
TPCDS_LOCAL=/Users/Will/Desktop/tpcds_tmp
BIGFRAME_OPTS="${BIGFRAME_OPTS} -Dbigframe.tpcds.local=${TPCDS_LOCAL}"

# Local Directory to store the itermediate data used for data refershing
REFRESH_LOCAL=~/bigframe_refresh_data
BIGFRAME_OPTS="${BIGFRAME_OPTS} -Dbigframe.refresh.local=${REFRESH_LOCAL}"

# Global Output Path
export OUTPUT_PATH="hdfs://localhost:9000/test_output"


###################################################################
# GLOBAL PARAMETERS USED BY DATA REFRESHING (REQUIRED)
###################################################################

# The Kafka Home Directory
KAFKA_HOME=""
BIGFRAME_OPTS="${BIGFRAME_OPTS} -Dbigframe.kafka.home=${KAFKA_HOME}"

# The list of brokers that the data refreshing driver can connnect to 
KAFKA_BROKER_LIST=""
BIGFRAME_OPTS="${BIGFRAME_OPTS} -Dbigframe.kafka.broker.list=${KAFKA_BROKER_LIST}"

# The address of zookeeper
ZOOKEEPER_CONNECT=""
BIGFRAME_OPTS="${BIGFRAME_OPTS} -Dbigframe.zookeeper.connect=${ZOOKEEPER_CONNECT}"


