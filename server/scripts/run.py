from utils.execute import execute, execute_save_output

docker_result = execute_save_output(
    "docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=password -v springular-db-volume:/var/lib/postgresql/data postgres")

execute(command="gradle bootRun --args='--spring.profiles.active=dev'",
        cleanup_command=f"docker rm -f {docker_result.stdout}")

execute(command=f"docker rm -f {docker_result.stdout}")
