# SpringBootCRUD

GET:
http://localhost:8084/customer/saveAllDatas endpoint'i ile DataUtil class'ı içerisindeki veriler veri tabanına kaydedilecetir.

![image](https://user-images.githubusercontent.com/29101297/155893842-e9756793-fe7c-4738-93cd-2893ae7e5428.png)

Diğer endpoint bilgileri ise şu şekilde kullanılabilir.

GETS:

http://localhost:8084/customer/listAll

http://localhost:8084/customer/list/13

http://localhost:8084/customer/branch/1

POSTS:

http://localhost:8084/customer/create

http://localhost:8084/customer/18/create/branch/

http://localhost:8084/customer/18/removeBranch/12

PUTS:

http://localhost:8084/customer/update/23

DELETES:

http://localhost:8084/customer/delete/23


Branch'ler için aynı şekilde yukarıdaki gibi endpointleri oluşturabilirsiniz.

GETS:

http://localhost:8084/branch/listAll
