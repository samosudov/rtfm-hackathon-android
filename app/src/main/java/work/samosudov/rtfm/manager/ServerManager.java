package work.samosudov.rtfm.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import timber.log.Timber;

/**
 * Created by samosudovd on 08/05/2018.
 */

public class ServerManager {

    private final static String BASE_URL = "http://ec2-3-82-45-111.compute-1.amazonaws.com:80/api/";
//    private final static int port = 7878;
//    private ManagedChannel channel = null;
//
//    private static ServerManager instance;
//    private ServerManager() {
//        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
//    }
//    public static ServerManager getInstance() {
//        if (instance == null) {
//            instance = new ServerManager();
//        }
//        return instance;
//    }
    private static ProtoManager protoManager;
    private static Retrofit protoRetrofit;

    //endpoints
    private final static String RECENT_PAYMENTS = "recent_payments";
    private final static String VALID_LIST = "valid_list";

    //Heroku prod
    public static ProtoManager protoApi() {
        if (protoManager == null) {
            if (protoRetrofit == null) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                protoRetrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(getNewHttpClient())
                        .addConverterFactory(ProtoConverterFactory.create())
//                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            }
            protoManager = protoRetrofit.create(ProtoManager.class);
        }

        return protoManager;
    }

    private static OkHttpClient getNewHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);

        return client.build();
    }

    public interface ProtoManager {

        @GET(VALID_LIST)
        Call<ResponseBody> validList();
        @GET(RECENT_PAYMENTS)
        Call<ResponseBody> estimatedObs();
//        @POST(RECENT_PAYMENTS)
//        Observable<Byte> estimatedObs();
//        Observable<Byte> estimatedObs(@Header("api-key") String headerApiKey,
//                                                   @Header("sign") String headerSign,
//                                                   @Body JsonObject JsonObj);
    }

    public void checkProto() {
        try {

//            OtherModels.Payment payment = pa
//             stub = CompactTxStreamerGrpc.newBlockingStub(channel);
//
//
//            l = stub.getBlockRange(br);

        } catch (Exception e) {
            Timber.e("checkProto from=%s", e.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
        }
    }
}
