messagesDE, messagesEN, messagesNL, messagesNormal = open('messages_de.properties', 'w'), open('messages_en.properties', 'w'), open('messages_nl.properties', 'w'), open('messages.properties', 'w')

for x in open('c:\\Users\\Is4ki\\Desktop\\semn6\\projekt\\src\\main\\resources\\uebersetzungen.csv', 'r'):
    splitCSV = x.split(";")
    messagesNormal.write(f"{splitCSV[0]}={splitCSV[1]}" + "\n")
    messagesDE.write(f"{splitCSV[0]}={splitCSV[1]}" + "\n")
    messagesEN.write(f"{splitCSV[0]}={splitCSV[2]}" + "\n")
    messagesNL.write(f"{splitCSV[0]}={splitCSV[3]}")