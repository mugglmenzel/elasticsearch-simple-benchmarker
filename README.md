# elasticsearch-simple-benchmarker

A simple benchmarking tool for Elasticsearch clusters. The benchmark puts a sample of documents into the cluster and measures the time (including connection setup) to finish the job.

## Usage

After cloning the repository, use SBT to run the main class with the following parameters. 

```bash
sbt "run -s [sample size] -e [elasticsearch host] -p [elasticsearch port] -n [elasticsearch cluster name] [-h]"
```
You can leave out any parameters to see the usage message (```sbt run```).

To run the benchmark against the HTTP port:

```bash
sbt "run -s [sample size] -e [elasticsearch host] -p [elasticsearch http port] -n [elasticsearch cluster name] -h"
```

To run the benchmark against the TCP port:

```bash
sbt "run -s [sample size] -e [elasticsearch host] -p [elasticsearch tcp port] -n [elasticsearch cluster name]"
```