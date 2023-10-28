import java.util.HashMap;
import java.util.Map;

public class ClassContainer {
    private Map<String, ClassEmployee> kontenery = new HashMap();

    public void addClass(String nazwa, double ilosc) {
        if (!kontenery.containsKey(nazwa)) {
            kontenery.put(nazwa, new ClassEmployee());
            ClassEmployee znalezione = kontenery.get(nazwa);
            for(int i = 0; i < ilosc; i++)
            {
                znalezione.addEmployee(new Employee("Jan", "Kowalski", EmployeeCondition.obecny, 1994, 12));
            }
        } else {
            System.out.println("Grupa o nazwie " + nazwa + " już istnieje.");
        }
    }

    public void removeClass(String nazwa) {
        if(kontenery.containsKey(nazwa))
        {
            kontenery.remove(nazwa);
        }
        else
        {
            System.out.println("Grupa o nazwie " + nazwa + " już istnieje.");
        }
    }

    public void findEmpty()
    {
        kontenery.forEach((groupName, group) -> {
            if (group.getSize() == 0)
                System.out.println(groupName);
        });
    }

    public void summary()
    {
        kontenery.forEach((groupName, group) -> {
            group.summary();
        });
    }

}
