import subprocess
import sys


def execute(command, cleanup_command=None):
    print(f"- Run: {command}")
    try:
        result = subprocess.run(command, shell=True, check=True)
        print(f"{command} finished successfully")
        return result
    except subprocess.CalledProcessError as e:
        if cleanup_command is not None:
            execute(cleanup_command)
        print(f"Error while executing {command}: {e}")
        sys.exit(1)
    except KeyboardInterrupt:
        if cleanup_command is not None:
            execute(cleanup_command)
        sys.exit(0)


def execute_save_output(command, cleanup_command=None):
    print(f"- Run: {command}")
    try:
        result = subprocess.run(command, shell=True,
                                check=True, capture_output=True, text=True)
        print(f"{command} finished successfully")
        return result
    except subprocess.CalledProcessError as e:
        if cleanup_command is not None:
            execute(cleanup_command)
        print(f"Error while executing {command}: {e}")
        sys.exit(1)
    except KeyboardInterrupt:
        if cleanup_command is not None:
            execute(cleanup_command)
        sys.exit(0)
