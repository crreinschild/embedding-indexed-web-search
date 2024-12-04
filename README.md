# embedding-indexed-web-search
Personal attempt to design a search engine around indexing embeddings for web pages rather than keywords. The hope is that with a descriptive enough search query, I can get higher quality web url search results. Worst case, it doesn't work, but I gain experience dealing with AI embedding models.

## Prerequisites

The following tools are required to run the project.

### Docker

I use [Docker Desktop](https://www.docker.com/products/docker-desktop/). Any other containerization engine should work, but for Windows, the easiest for me is Docker Desktop.

### Ollama (Containerized)

To minimize the setup & configuration required for a local deployment, I elected to deploy the [official Ollama docker image](https://hub.docker.com/r/ollama/ollama).

If you're confident, you can try to run either of the following commands in a terminal:

```sh
# CPU Only
docker run -d -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama

# Nvidia-accelerated
docker run -d --gpus=all -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
```

#### GPU Acceleration

If you're in Windows and are using an Nvidia GPU and have the Nvidia App installed, it should just work. If you are not me, you may want to follow the instructions on the [Ollama Docker Hub page](https://hub.docker.com/r/ollama/ollama) to set up docker to work with your GPU.

#### Testing the setup

If you're like me and want to make sure it all works, open up Docker Desktop, select the ollama container, and go to the Exec tab.

In the Exec tab, you can run the following command to start up a relatively lightweight (2GB) chatbot to test with.

```sh
ollama run llama3.2
```

The responses should be relatively quick (generating paragraphs seconds or less) if the GPU acceleration is working. With CPU only, it takes several seconds to generate long paragraphs. Since we are generating embeddings with a relatively small model, you should not see significant performance issues in testing.

### Elasticsearch (Containerized)

Again, to minimize the local setup time and complexity, we will follow [Elastic's documented instructions to set up Elasticsearch in Docker](https://www.elastic.co/guide/en/elasticsearch/reference/current/run-elasticsearch-locally.html).

> Note: According to their documenation, if you're in Windows, you'll need to install and run WSL since they only provide a shell script
> Do this by running `wsl --install` in a terminal.

If you just want the commands to run, proceed with the following:

1. Open bash (WSL in Windows)
2. Copy, paste, and execute `curl -fsSL https://elastic.co/start-local | sh`
   1. NOTE: This command will create a new directory with some state stuff, so make sure you are in a directory you like to have stuff in.
3. Note the generated local password and API Key (don't worry if you lose it, it's in the `.env` file of the created directory above)
4. Test that it's working by going to `http://localhost:5601` and logging in with `elastic` and the password noted above

#### Note about the API Key

At this particular moment, I'm still hardcoding the API Key. Soon, it will move to a config file. And after that, once I get this code running in a container orchestration platform (maybe K8s), it will move to a secret.

#### Creating the test index

To start indexing stuff, we need to make sure we register the index (there is a vector field type that is required for vector search).

> TODO: Turn this into a script

1. Open up the local Kibana if you haven't already and log in with the local credentials.
2. Open the menu, and near the bottom, go to Dev Tools
3. Replace the Console values with the following and run it
```
PUT /test-index
{
    "mappings": {
        "properties": {
            "content-embedding": {
                "type": "sparse_vector"
            },
            "id": {
                "type": "text"
            },
            "url": {
                "type": "text"
            },
            "title": {
                "type": "text"
            }
        }
    }
}
```

> NOTE: This index mapping is subject to change during development. Remember to update the above. If you want to maintain the previously indexed data, you will want to create a new index with the new mappings, then migrate the old data to the new index.

## Running

TODO