import subprocess
import sys


def execute(command):
    print(f"- Run: {command}")
    try:
        subprocess.run(command, shell=True, check=True)
        print(f"{command} finished successfully")
    except subprocess.CalledProcessError as e:
        print(f"Error while executing {command}: {e}")
        sys.exit(1)
    except KeyboardInterrupt:
        sys.exit(0)
