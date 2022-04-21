@ REM Runs locally a new container.

docker run -p 8080:8080 --name dip -it dip:1-SNAPSHOT

docker container rm dip