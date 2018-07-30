package com.but42.halva.ui.list

import com.but42.halva.repository.Repository
import com.but42.halva.ui.base.ViewModelBase
import javax.inject.Inject

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
interface ListViewModel

class ListViewModelImpl @Inject constructor(
        repository: Repository
) : ViewModelBase(repository), ListViewModel