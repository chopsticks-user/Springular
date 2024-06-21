from utils.execute import execute, execute_save_output

docker_result = execute_save_output(
    "docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=password -v springular-db-volume:/var/lib/postgresql/data postgres")
execute(command="gradle bootRun",
        cleanup_command=f"docker stop {docker_result.stdout}")
