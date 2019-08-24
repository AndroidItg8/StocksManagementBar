package itg8.com.stockmanagement.common;

import androidx.databinding.BaseObservable;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

public class FragmentSupportBaseObservable extends BaseObservable {
    private WeakReference<Fragment> weakFragment;


    public FragmentSupportBaseObservable(Fragment fragment) {
        weakFragment = new WeakReference<>(fragment);
    }

    public Fragment getFragment() {
        return weakFragment.get();
    }

}

