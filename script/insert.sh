#!/bin/bash

function_fork() {
	for (( i=$1; i<$1+1000; i++ ))
	do
		curl -d "insertData=smilegate$i:$i" http://localhost:8080/api/insertRankingData
	done
}

function_fork 0 &
function_fork 1000 &
function_fork 2000 &
function_fork 3000 &
function_fork 4000 &
function_fork 5000 &
function_fork 6000 &
function_fork 7000 &
function_fork 8000 &
function_fork 9000 &

