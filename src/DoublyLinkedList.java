import java.util.ArrayList;
import java.util.List;

public class DoublyLinkedList {
    private class Node {
        Contact contact;
        Node next;
        Node prev;

        Node(Contact contact) {
            this.contact = contact;
        }
    }

    private Node head;
    private Node tail;

    public void add(Contact contact) {
        Node newNode = new Node(contact);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public void delete(String name) {
        Node current = head;
        while (current != null) {
            if (current.contact.getName().equals(name)) {
                if (current == head) {
                    head = current.next;
                }
                if (current == tail) {
                    tail = current.prev;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                }
                if (current.prev != null) {
                    current.prev.next = current.next;
                }
                return;
            }
            current = current.next;
        }
    }

    public List<Contact> search(String prefix) {
        List<Contact> result = new ArrayList<>();
        Node current = head;
        while (current != null) {
            if (current.contact.getName().startsWith(prefix)) {
                result.add(current.contact);
            }
            current = current.next;
        }
        return result;
    }

    public List<Contact> getAllContacts() {
        List<Contact> result = new ArrayList<>();
        Node current = head;
        while (current != null) {
            result.add(current.contact);
            current = current.next;
        }
        return result;
    }
}
