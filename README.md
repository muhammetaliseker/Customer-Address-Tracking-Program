# Customer-Address-Tracking-Program
Customer Address Tracking Program

Before we start:
We must import external library called "rs2xml"
You also must import schemas as well.


Purpose of the project: It is aimed to track, change or add the address of the products received by customers by one or more managers. At the same time, the Customer can receive the product, delete (return) the product he received.
![image](https://github.com/muhammetaliseker/Customer-Address-Tracking-Program/assets/67159734/f9e8eb49-031b-4627-a0be-c57175a1b74e)
Home Screen:
I have divided my project into two as customer and manager.Now I will tell you about the user side.
![image](https://github.com/muhammetaliseker/Customer-Address-Tracking-Program/assets/67159734/48b32ab2-bd82-44cc-8bcf-2a64e5ff0d80)
The user has been asked to get angry. Here, users are recorded in the schema named "k_giris.kullanici_girisi" in Mysql. If the user wants to register, add a new user to the scheme by registering. Assume that the user is logged in.
![image](https://github.com/muhammetaliseker/Customer-Address-Tracking-Program/assets/67159734/8d15f4e6-bd02-46fa-9085-cee44ad38449)
A form with two tabs appears. In the first tab, the user can write the product they want to buy and where the product should go. Addresses are drawn from the “tr_adres” schema. I downloaded this schema from the internet so that more accurate addresses would be reflected. In this way, all the addresses of all 81 provinces of Turkiye are included in this project.
![image](https://github.com/muhammetaliseker/Customer-Address-Tracking-Program/assets/67159734/0b6dd661-f7fa-4579-b99a-c0953fe971db)
In the Basket tab, the products that the Customer has previously purchased are displayed. And the customer has the authority to delete these products if he wishes.
That's it for the customer part. Let's move on to the admin section.
![image](https://github.com/muhammetaliseker/Customer-Address-Tracking-Program/assets/67159734/9c726217-9f13-42c8-be84-5c2319332cc3)
The administrator part also logs in just like the user, and the login data here is “k_giris.yonetici_bilgiler" is being withdrawn from the schema. There is no registration here. It is not desirable for a random person to register and access all the data.
![image](https://github.com/muhammetaliseker/Customer-Address-Tracking-Program/assets/67159734/7c3b4490-4867-496c-8a41-5c0fdd0087c4)
After the administrator logs in, a form with 2 tabs appears. In the first tab, the administrator can see what all users in his system have received and received. At the same time, it can delete products and add new products. The “Add/Update” Button in the photo is only active when you select a row from the list. When you select it from the list and press the button, you can move to the next tab and purchase a new product with the information of the selected user or update the elements of the selected row. For example, I will select the individual with the username “yusuf123” in the list.
![image](https://github.com/muhammetaliseker/Customer-Address-Tracking-Program/assets/67159734/64a66a7f-0402-4818-b5c1-4bca3ac241df)
If we select one from the list and press the add button, the necessary information in the list is automatically placed in the blanks. After entering the product and address, if he presses the "Add Product" button, a new product is added to the list, and if he presses the "Update" button, the information of the selected element in the list is updated. The “Clear” Button also resets the information and makes it clear.

Let's examine what happens if we don't select one in the list and press the "Add/Update" button and switch to the "Add/Update" tab manually.
![image](https://github.com/muhammetaliseker/Customer-Address-Tracking-Program/assets/67159734/d12cc7ed-84a2-419c-bbcc-fee4a7464edf)
If the administrator moves to the next tab at his own request, he must enter all the information here himself. If the username entered is not found in the list, it creates a user record and adds a new user to the "k_giris.kullanici_girisi" schema. Even though the username exists, if the other elements are entered incorrectly, an error will occur. Even if he enters the other elements correctly, a new element is added as if he were selecting an element from the list and pressing the "Add/Update" button, but he cannot benefit from the "update" button here because there is no selected element in the list.







