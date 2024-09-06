#!/usr/bin/env bash


start_project() {
  local project_dir="$1"
  local project_name=$1


  if [ -d Companies_MS/ ]; then
    echo "Starting project in $project_dir..."
    tmux new-session -d -s "$project_name"
    echo "Started new tmux session with name $project_name"
    tmux send-keys -t "$project_name" "cd $project_dir && mvn spring-boot:run" C-m
    echo "INFO: to access session use: tmux attach -t $project_name"

  else
    echo "$project_dir does not exist!"
  fi
}
pwd
start_project "Companies_MS/"
start_project "Projects_MS/"
start_project "Users_MS/"

echo "###################################################"
echo "INFO: to scroll use ctrl + b, than ["
echo "INFO: to detach from session use ctrl + b, then d"
echo "INFO: to close Spring Boot app use ctrl + d"
echo "INFO: to kill all use tmux kill-server"
