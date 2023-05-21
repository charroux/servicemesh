# Cloud native app
## Docker and Kubernetes
Start Docker.

Start the Kubernetes cluster:
```
minikube start --cpus=2 --memory=5000 --driver=docker
```
## Database and microservices gateway
<img src="images/servicemesh.png">

Launch the Kubernetes deployment and service for PostgreSQL, and the Ingress gataway:
```
kubectl apply -f infrastructure.yaml
```
## Microservices and service mesh proxies

```
kubectl apply -f microservices.yaml
```
## Get the access to the Ingress gateway
```
./ingress-forward.sh
```
```
kubectl -n istio-system port-forward deployment/istio-ingressgateway 31380:8080
```
Ask carservice the list of cars:
```
http://localhost:31380/carservice/cars
```

## Fault tolerance without coding

### Circuit breaker
<img src="images/circuit_breaker.png">

Adding a circuit breaker to carservice:
```
kubectl apply -f circuit-breaker.yaml
```
Test the circuit breaker:
http://localhost:31380/carservice/cars

Disable the circuit breaker using:
```
kubectl delete -f circuit-breaker.yaml
```
### Auto restart in case of failure
```
kubectl get pods
```
```
kubectl delete pods [pod name]
```

## Scaling and Load balancing without coding
<img src="images/scaling.png">

How many instance are actually running:
```
kubectl get pods
```
```
kubectl get deployments
```
Start a second instance:
```
kubectl scale --replicas=2 deployment/[deployment name]
```
## Monotoring
### Display the Kiali dashboard
Kiali is a console for Istio service mesh.
```
kubectl -n istio-system port-forward deployment/kiali 20001:20001
```
Launch the console: http://localhost:20001/
## Stop the app
```
kubectl delete -f microservices.yaml
```
```
kubectl delete -f infrastructure.yaml
```
# Rest Web service implementation
<img src="images/carservicearchi.png">

## Rest web service
https://github.com/charroux/servicemesh/blob/main/carservice/src/main/java/com/charroux/carservice/web/CarRentalRestService.java

# gRPC
## Service contract
https://github.com/charroux/servicemesh/blob/main/carservice/src/main/proto/carservice.proto
<img src="images/full_duplex.png">

## Client side
https://github.com/charroux/servicemesh/blob/main/carservice/src/main/java/com/charroux/carservice/service/RentalServiceImpl.java
## Server side
https://github.com/charroux/servicemesh/blob/main/carstat/src/main/java/com/charroux/carstat/CarRentalServiceImpl.java

# Distributed transactions
## The saga pattern
<img src="images/sagapattern.png">

<img src="images/saga.png">

https://github.com/charroux/servicemesh/blob/main/carservice/src/main/java/com/charroux/carservice/service/RentalServiceImpl.java

# Kubernetes
## Pod and Service
<img src="images/pod_service.png">

https://github.com/charroux/servicemesh/blob/main/infrastructure.yaml

Launch the Kubernetes deployment and service for PostgreSQL:
```
kubectl apply -f infrastructure.yaml
```
```
minikube dashboard
```
## Ingress gateway
<img src="images/servicemesh.png">

https://github.com/charroux/servicemesh/blob/main/infrastructure.yaml
### Microservices, service mesh proxies and routing via the gateway
```
kubectl apply -f microservices.yaml
```
https://github.com/charroux/servicemesh/blob/main/microservices.yaml
### Get the access to the Ingress gateway
```
./ingress-forward.sh
```
Ask carservice the list of cars:
```
http://localhost:31380/carservice/cars
```
# Service mesh
## Fault tolerance
### Circuit breaker
<img src="images/circuit_breaker.png">

Adding a circuit breaker to carservice:
```
kubectl apply -f circuit-breaker.yaml
```
Test the circuit breaker:
http://localhost:31380/carservice/cars

https://github.com/charroux/servicemesh/blob/main/circuit-breaker.yaml

Disable the circuit breaker using:
```
kubectl delete -f circuit-breaker.yaml
```

## Scaling and Load balancing without coding
<img src="images/scaling.png">

How many instance are actually running:
```
kubectl get pods
```
```
kubectl get deployments
```
Start a second instance:
```
kubectl scale --replicas=2 deployment/[deployment name]
```

curl --header "Content-Type: application/json" --request POST --data '{"customerId":1,"numberOfCars":2}' http://localhost:31380/carservice/cars


# How to Run
## Install Docker and Minikube
https://www.docker.com/get-started/

https://minikube.sigs.k8s.io/docs/start/

Then start the Kubernetes cluster:
```
minikube start --cpus=2 --memory=5000 --driver=docker
```
## Install Istio
https://istio.io/latest/docs/setup/getting-started/
```
cd istio-1.17.0    
export PATH=$PWD/bin:$PATH    
istioctl install --set profile=demo -y
cd ..   
```
Enable auto-injection of the Istio side-cars when the pods are started:
```
kubectl label namespace default istio-injection=enabled
```
Install the Istio addons (Kiali, Prometheus, Jaeger, Grafana):
```
kubectl apply -f samples/addons
```
## 
Enable auto-injection of the Istio side-cars when the pods are started:
```
kubectl label namespace default istio-injection=enabled
```

Configure Docker so that it uses the Kubernetes cluster:
```
minikube docker-env
eval $(minikube -p minikube docker-env)
eval $(minikube docker-env)  
```
Build the postgres image:
```
docker build --tag=charroux/postgres:1 postgres
docker push charroux/postgres:1
```
Build the carservice app:
```
cd carservice
./gradlew build
cd ..
docker build --tag=charroux/carservice:1 carservice
docker push charroux/carservice:1  
```
Build the carstat app:
```
cd carstat
./gradlew build
cd ..
docker build --tag=charroux/carstat:1 carstat  
docker push charroux/carstat:1  
```
Build the customer app:
```
cd customer
./gradlew build
cd ..
docker build --tag=charroux/customer:1 customer  
docker push charroux/customer:1  
```
Launch the Kubernetes deployment and service for PostgreSQL, and the Ingress gataway:
```
kubectl apply -f infrastructure.yaml
```
See the configuration: https://github.com/charroux/servicemesh/blob/main/infrastructure.yaml

Launch:
- Kubernetes deployment and service for carcervice
- Istio Virtual Service for carservice (a VirtualService defines a set of traffic routing rules to apply when a host is addressed)
- Kubernetes deployment and service for the customer app
- Istio Virtual Service for the customer app
```
kubectl apply -f microservices.yaml
```
See the configuration: https://github.com/charroux/servicemesh/blob/main/microservices.yaml

Inspect the logs of the pods:
```
kubectl get pods
```
```
kubectl logs [pod name] carservice
```
Enter the Docker containers:
```
kubectl exec -it [pod name] -- /bin/sh
```
```
ls
```
You should view the java jar file.
```
exit
```
Use Minikube to reach the carservice:
```
minikube service carservice --url
```
```
http://127.0.0.1:[port]/cars
```
Get the access to the Ingress gateway:
```
./ingress-forward.sh
```
Ask carservice the list of cars:
```
http://localhost:31380/carservice/cars
```
The carservice has been configured in the carservice Virtual Service (see https://github.com/charroux/servicemesh/blob/main/microservices.yaml).
## Display the Kiali dashboard
Kiali is a console for Istio service mesh.
```
kubectl -n istio-system port-forward deployment/kiali 20001:20001
```
Launch the console: http://localhost:20001/

Active again carservice: http://localhost:ingressport/carservice/cars, hen inspect the cluster in Kiali.
http://localhost:ingressport/carservice/cars

<img src="images/kiali1.png">

curl --header "Content-Type: application/json" --request POST --data '{"customerId":1,"numberOfCars":2}' http://localhost:31380/carservice/cars

<img src="images/kiali2.png">

kubectl -n istio-system port-forward deployment/grafana 3000:3000

http://localhost:3000/

<img src="images/graphana_resources.png">

<img src="images/graphana_istio_dashboard.png">

<img src="images/graphana-envoy.png">


http://localhost:ingressport/carservice/agreements

curl --header "Content-Type: application/json" --request POST --data '{"customerId":1,"numberOfCars":2}' http://localhost:31380/carservice/cars

curl --header "Content-Type: application/json" --request POST --data '{"customerId":1,"numberOfCars":1}' http://localhost:31380/carservice/cars


kubectl delete -f infrastructure.yaml

kubectl delete -f microservices.yaml
