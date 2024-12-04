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

### Elasticsearch

TODO