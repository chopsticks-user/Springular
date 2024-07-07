from utils.execute import execute, execute_save_output
import argparse
import sys

parser = argparse.ArgumentParser()
parser.add_argument("container_name", nargs='?',
                    default="$(docker ps -a -q)")
args = parser.parse_args()

execute(
    command=f"docker exec -it {args.container_name} psql -U postgres -d postgres")
