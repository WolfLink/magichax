package me.nathanp.magiccreatures.model;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;

public class API {

    public static final int OK = 0;
    public static final int CANCELLED = 1;
    public static final int UNKNOWN = 2;
    public static final int INVALID_ARGUMENT = 3;
    public static final int DEADLINE_EXCEEDED = 4;
    public static final int NOT_FOUND = 5;
    public static final int ALREADY_EXISTS = 6;
    public static final int PERMISSION_DENIED = 7;
    public static final int RESOURCE_EXHAUSTED = 8;
    public static final int FAILED_PRECONDITION = 9;
    public static final int ABORTED = 10;
    public static final int OUT_OF_RANGE = 11;
    public static final int UNIMPLEMENTED = 12;
    public static final int INTERNAL = 13;
    public static final int UNAVAILABLE = 14;
    public static final int DATA_LOSS = 15;
    public static final int UNAUTHENTICATED = 16;

    private static int translateCode(FirebaseFunctionsException.Code code) {
        switch (code) {
            case OK:
                return OK;
            case CANCELLED:
                return CANCELLED;
            case UNKNOWN:
                return UNKNOWN;
            case INVALID_ARGUMENT:
                return INVALID_ARGUMENT;
            case DEADLINE_EXCEEDED:
                return DEADLINE_EXCEEDED;
            case NOT_FOUND:
                return NOT_FOUND;
            case ALREADY_EXISTS:
                return ALREADY_EXISTS;
            case PERMISSION_DENIED:
                return PERMISSION_DENIED;
            case RESOURCE_EXHAUSTED:
                return RESOURCE_EXHAUSTED;
            case FAILED_PRECONDITION:
                return FAILED_PRECONDITION;
            case ABORTED:
                return ABORTED;
            case OUT_OF_RANGE:
                return OUT_OF_RANGE;
            case UNIMPLEMENTED:
                return UNIMPLEMENTED;
            case INTERNAL:
                return INTERNAL;
            case UNAVAILABLE:
                return UNAVAILABLE;
            case DATA_LOSS:
                return DATA_LOSS;
            case UNAUTHENTICATED:
                return UNAUTHENTICATED;
            default:
                return UNKNOWN;
        }
    }

    public static String codeToString(int code) {
        switch (code) {
            case OK:
                return "OK";
            case CANCELLED:
                return "CANCELLED";
            case UNKNOWN:
                return "UNKNOWN";
            case INVALID_ARGUMENT:
                return "INVALID_ARGUMENT";
            case DEADLINE_EXCEEDED:
                return "DEADLINE_EXCEEDED";
            case NOT_FOUND:
                return "NOT_FOUND";
            case ALREADY_EXISTS:
                return "ALREADY_EXISTS";
            case PERMISSION_DENIED:
                return "PERMISSION_DENIED";
            case RESOURCE_EXHAUSTED:
                return "RESOURCE_EXHAUSTED";
            case FAILED_PRECONDITION:
                return "FAILED_PRECONDITION";
            case ABORTED:
                return "ABORTED";
            case OUT_OF_RANGE:
                return "OUT_OF_RANGE";
            case UNIMPLEMENTED:
                return "UNIMPLEMENTED";
            case INTERNAL:
                return "INTERNAL";
            case UNAVAILABLE:
                return "UNAVAILABLE";
            case DATA_LOSS:
                return "DATA_LOSS";
            case UNAUTHENTICATED:
                return "UNAUTHENTICATED";
            default:
                return "UNKNOWN";
        }
    }

    public static abstract class Then<T> {
        protected abstract void ok(T data);
        protected abstract void cancelled(String reason);
    }

    public static void testFunction(final Then<String> then) {
        FirebaseFunctions functions = FirebaseFunctions.getInstance();
        functions.getHttpsCallable("testFunction").call()
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) {
                        return task.getResult().getData().toString();
                    }
                }).addOnCompleteListener(new APICompleteListener<String>() {
            @Override
            public void APIComplete(@NonNull String value) {
                then.ok(value);
            }

            @Override
            public void APIFail(int status) {
                then.cancelled(codeToString(status));
            }
        });
    }

    public static void isReadyToPlay(final Then<Boolean> then) {
        FirebaseFunctions functions = FirebaseFunctions.getInstance();
        functions.getHttpsCallable("isReadyToPlay").call()
            .continueWith(new Continuation<HttpsCallableResult, Boolean>() {
                @Override
                public Boolean then(@NonNull Task<HttpsCallableResult> task) {
                    if (task.getResult() != null) {
                        String result = (String) task.getResult().getData();
                        return result.equals("Yes");
                    } else {
                        return false;
                    }
                }
            }).addOnCompleteListener(new APICompleteListener<Boolean>() {
                @Override
                public void APIComplete(@NonNull Boolean value) {
                    then.ok(value);
                }

                @Override
                public void APIFail(int status) {
                    then.cancelled(codeToString(status));
                }
        });
    }

    public static void getAvailableCards(Player player, final Then<ArrayList<Card>> then) {
        final ArrayList<Card> cards = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference cardsRef = database.getReference("cards");
        for (String card : player.getCards()) {
            cardsRef.child(card).addListenerForSingleValueEvent(new ValueEventListener<Card>(Card.class) {
                @Override
                public void onEvent(Card data) {
                    cards.add(data);
                }
            });
        }
        then.ok(cards);
    }

    public static void getCardCosts(final Then<HashMap<String, Integer>> then) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference cardCostRef = database.getReference("cardCosts");
        cardCostRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Integer> result = new HashMap<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    result.put(ds.getKey(), ds.getValue(Integer.class));
                }
                then.ok(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                then.cancelled(databaseError.toString());
            }
        });
    }

    public static void getPlayer(String uid, ValueEventListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(Paths.getPlayerDocumentPath(uid)).addListenerForSingleValueEvent(listener);
    }

    public static void getCreature(final Then<Creature> then) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.getReference(Paths.getPlayerCreaturesCollectionPath(user.getUid())).addListenerForSingleValueEvent(new ValueEventListener<Creature>(Creature.class) {
                @Override
                public void onEvent(Creature data) {
                    then.ok(data);
                }
            });
        }
    }

    public static void getCurrentGame(String uid, ValueEventListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(Paths.getPlayerDocumentPath(uid)).addListenerForSingleValueEvent(listener);
    }

    public static void setOnCurrentGameChangedListener(String uid, ValueEventListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(Paths.getPlayerCurrentGamePath(uid)).addValueEventListener(listener);
    }

    public static abstract class APICompleteListener<T> implements OnCompleteListener<T> {

        @Override
        public void onComplete(@NonNull Task<T> task) {
            if (task.isSuccessful()) {
                APIComplete(task.getResult());
            } else {
                Exception e = task.getException();
                if (e instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                    FirebaseFunctionsException.Code code = ffe.getCode();
                    Object details = ffe.getDetails();
                    APIFail(translateCode(code));
                }
            }
        }

        public abstract void APIComplete(@NonNull T value);
        public abstract void APIFail(int status);
    }

    public static abstract class ValueEventListener<T> implements com.google.firebase.database.ValueEventListener {
        private Class<T> clazz;
        protected ValueEventListener(Class<T> clazz) {
            this.clazz = clazz;
        }

        public abstract void onEvent(T data);

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists())
                onEvent(dataSnapshot.getValue(clazz));
            else
                onEvent(null);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            onEvent(null);
        }
    }
}
