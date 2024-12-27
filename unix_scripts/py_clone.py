import os
import subprocess
import sys
from urllib.parse import urlparse, urlsplit

def clone_specific_commit(commit_url):
    try:
        # Parse the URL to extract repository and commit hash
        parsed_url = urlparse(commit_url)
        if 'github.com' not in parsed_url.netloc or '/commit/' not in parsed_url.path:
            print("Invalid GitHub commit URL")
            return

        path_parts = parsed_url.path.split('/commit/')
        repo_path = path_parts[0]
        commit_hash = path_parts[1]

        # Construct the repository clone URL
        repo_url = f"https://github.com{repo_path}.git"
        repo_name = os.path.basename(repo_path)

        # Clone the repository without checkout
        print(f"Cloning repository {repo_url}...")
        subprocess.run(['git', 'clone', '--no-checkout', repo_url], check=True)
        
        # Change directory to the repository
        os.chdir(repo_name)

        # Fetch and checkout the specific commit
        print(f"Fetching commit {commit_hash}...")
        subprocess.run(['git', 'fetch', 'origin', commit_hash], check=True)
        print(f"Checking out commit {commit_hash}...")
        subprocess.run(['git', 'checkout', commit_hash], check=True)

        print(f"Repository cloned and checked out to commit {commit_hash}")
    except subprocess.CalledProcessError as e:
        print(f"Error during Git operation: {e}")
    except Exception as e:
        print(f"An unexpected error occurred: {e}")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python clone_commit.py <commit_url>")
    else:
        clone_specific_commit(sys.argv[1])
