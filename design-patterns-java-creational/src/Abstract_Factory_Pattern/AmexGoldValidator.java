package Abstract_Factory_Pattern;

public class AmexGoldValidator extends VisaValidator implements Validator {

    @Override
    public boolean isValid(CreditCard creditCard) {

        return false;
    }

}
