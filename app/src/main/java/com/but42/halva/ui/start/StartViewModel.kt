package com.but42.halva.ui.start

import com.but42.halva.repository.Repository
import com.but42.halva.ui.base.ViewModelBase
import javax.inject.Inject

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
interface StartViewModel

class StartViewModelImpl @Inject constructor(
        repository: Repository
) : ViewModelBase(repository), StartViewModel