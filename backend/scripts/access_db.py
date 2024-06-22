from utils.execute import execute, execute_save_output
import argparse
import sys

parser = argparse.ArgumentParser()
parser.add_argument("container_name")
args = parser.parse_args()

execute(
    command=f"docker exec -it {args.container_name} psql -U postgres -d postgres")
